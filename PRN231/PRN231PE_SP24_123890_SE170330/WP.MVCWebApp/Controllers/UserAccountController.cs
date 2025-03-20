using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Mvc;
using WP.MVCWebApp.Models;

namespace WP.MVCWebApp.Controllers
{
    public class UserAccountController : Controller
    {
        private string APIEndPoint = "https://localhost:7015/api/";
        public IActionResult Index()
        {
            return RedirectToAction("Login");
        }

        public IActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Login(LoginRequest login)
        {

            try
            {
                using (var httpClient = new HttpClient())
                {
                    using (var response = await httpClient.PostAsJsonAsync(APIEndPoint + "UserAccount/Login", login))
                    {
                        if (response.IsSuccessStatusCode)
                        {
                            var tokenString = await response.Content.ReadAsStringAsync();

                            var tokenHandler = new JwtSecurityTokenHandler();
                            var jwtToken = tokenHandler.ReadToken(tokenString) as JwtSecurityToken;

                            if (jwtToken != null)
                            {
                                var email = jwtToken.Claims.FirstOrDefault(c => c.Type == ClaimTypes.Name).Value;
                                var role = jwtToken.Claims.FirstOrDefault(c => c.Type == ClaimTypes.Role)?.Value;

                                var claims = new List<Claim>
                        {
                            new Claim(ClaimTypes.Name, email),
                            new Claim(ClaimTypes.Role, role),
                        };

                                var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
                                await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(identity));

                                Response.Cookies.Append("Email", email);
                                Response.Cookies.Append("Role", role);
                                Response.Cookies.Append("TokenString", tokenString);

                                return RedirectToAction("Index", "WatercolorsPaintings");
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {

            }

            HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
            ModelState.AddModelError("", "Login failure");
            return View();
        }

        public async Task<IActionResult> Logout()
        {
            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);

            Response.Cookies.Delete("Email");
            Response.Cookies.Delete("Role");
            Response.Cookies.Delete("TokenString");

            return RedirectToAction("Login", "UserAccount");
        }

        public async Task<IActionResult> Forbidden()
        {
            return View();
        }
    }
}
