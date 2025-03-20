using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using WP.Repositories.Models;
using WP.Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace WP.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserAccountController : ControllerBase
    {
        private readonly IConfiguration _config;
        private readonly IUserAccountService _userAccountService;

        public UserAccountController(IConfiguration config, IUserAccountService userAccountService)
        {
            _config = config;
            _userAccountService = userAccountService;
        }

        [HttpPost("Login")]
        public IActionResult Login([FromBody] LoginReqeust request)
        {
            var user = _userAccountService.Authenticate(request.Email, request.Password);

            if (user == null || user.Result == null)
                return Unauthorized();

            var token = GenerateJSONWebToken(user.Result);

            return Ok(token);
        }

        private string GenerateJSONWebToken(UserAccount UserAccount)
        {
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["Jwt:Key"]));
            var credentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha256);

            var token = new JwtSecurityToken(_config["Jwt:Issuer"]
                    , _config["Jwt:Audience"]
                    , new Claim[]
                    {
                new(ClaimTypes.Name, UserAccount.UserEmail),
                new(ClaimTypes.Role, UserAccount.Role.ToString()),
                    },
                    expires: DateTime.Now.AddMinutes(120),
                    signingCredentials: credentials
                );

            var tokenString = new JwtSecurityTokenHandler().WriteToken(token);

            return tokenString;
        }

    }

    public sealed record LoginReqeust(string Email, string Password);
}
