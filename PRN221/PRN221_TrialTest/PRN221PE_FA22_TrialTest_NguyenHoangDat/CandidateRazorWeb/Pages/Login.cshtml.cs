using Candidate_BusinessObjects;
using Candidate_Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace CandidateRazorWeb.Pages
{
    public class LoginModel : PageModel
    {
        private IHRAccountService hraccountService;
        public LoginModel(IHRAccountService hRAccountService)
        {
            hraccountService = hRAccountService;
        }
        public void OnGet()
        {
        }
        public void OnPost()
        {
            string email = Request.Form["txtEmail"];
            string password = Request.Form["txtPassword"];
            Hraccount hraccount = hraccountService.GetHraccount(email);
            if (hraccount != null && hraccount.Password.Equals(password))
            {
                int? roleID = hraccount.MemberRole;
                HttpContext.Session.SetString("RoleID", roleID.ToString());
                Response.Redirect("/JobPostingPage/Index");
            }

        }
    }
}
