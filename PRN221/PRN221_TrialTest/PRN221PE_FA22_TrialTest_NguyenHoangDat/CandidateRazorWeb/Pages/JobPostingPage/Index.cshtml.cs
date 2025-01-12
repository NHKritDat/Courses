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
    public class IndexModel : PageModel
    {
        private readonly IJobPostingService jobService;

        public IndexModel(IJobPostingService jobPostService)
        {
            jobService = jobPostService;
        }

        public IList<JobPosting> JobPosting { get; set; } = default!;

        public async Task OnGetAsync()
        {
            if (jobService != null)
            {
                JobPosting = jobService.GetJobPosting();
            }
        }
    }
}
