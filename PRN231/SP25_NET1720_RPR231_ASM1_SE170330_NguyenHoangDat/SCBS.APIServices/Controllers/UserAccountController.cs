using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using SCBS.Repositories.Models;
using SCBS.Services;

namespace SCBS.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserAccountController : ControllerBase
    {
        private readonly IConfiguration _configuration;
        private readonly IUserAccountService _userAccountService;
        public UserAccountController(IUserAccountService userAccountService, IConfiguration configuration)
        {
            _userAccountService = userAccountService;
            _configuration = configuration;
        }
        [HttpPost("Login")]
        public IActionResult Login([FromBody] LoginReqeust request)
        {
            var user = _userAccountService.Authenticate(request.UserName, request.Password);

            if (user == null || user.Result == null)
                return Unauthorized();

            var token = GenerateJSONWebToken(user.Result);

            return Ok(token);
        }

        private string GenerateJSONWebToken(UserAccount systemUserAccount)
        {
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["Jwt:Key"]));
            var credentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha256);

            var token = new JwtSecurityToken(_configuration["Jwt:Issuer"]
                    , _configuration["Jwt:Audience"]
                    , new Claim[]
                    {
                new(ClaimTypes.Name, systemUserAccount.UserName),
                //new(ClaimTypes.Email, systemUserAccount.Email),
                new(ClaimTypes.Role, systemUserAccount.RoleId.ToString()),
                    },
                    expires: DateTime.Now.AddMinutes(120),
                    signingCredentials: credentials
                );

            var tokenString = new JwtSecurityTokenHandler().WriteToken(token);

            return tokenString;
        }

        public sealed record LoginReqeust(string UserName, string Password);
    }
}
