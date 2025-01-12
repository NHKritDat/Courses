using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using Candidate_BusinessObjects;
using Candidate_Daos;
using Candidate_Services;

namespace CandidateRazorWeb.Pages.JobPostingPage
{
    public class DetailsModel : PageModel
    {
        private readonly IJobPostingService _jobService;

        public DetailsModel(IJobPostingService jobService)
        {
            _jobService = jobService;
        }

      public JobPosting JobPosting { get; set; } = default!; 

        public async Task<IActionResult> OnGetAsync(string id)
        {
            if (id == null || _jobService.GetJobPosting() == null)
            {
                return NotFound();
            }

            var jobposting = _jobService.GetJobPosting(id);
            if (jobposting == null)
            {
                return NotFound();
            }
            else 
            {
                JobPosting = jobposting;
            }
            return Page();
        }
    }
}
