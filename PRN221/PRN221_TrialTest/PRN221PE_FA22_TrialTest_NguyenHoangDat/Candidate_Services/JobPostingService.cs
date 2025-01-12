using Candidate_BusinessObjects;
using Candidate_Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Candidate_Services
{
    public class JobPostingService : IJobPostingService
    {
        private readonly IJobPostingRepo _repo;
        public JobPostingService()
        {
            _repo = new JobPostingRepo();
        }
        public bool AddJobPosting(JobPosting jobPosting)
        {
            return _repo.AddJobPosting(jobPosting);
        }

        public JobPosting GetJobPosting(string jobId)
        {
            return _repo.GetJobPosting(jobId);
        }

        public List<JobPosting> GetJobPosting()
        {
            return _repo.GetJobPosting();
        }

        public bool RemoveJobPosting(string jobPostingId)
        {
            return _repo.RemoveJobPosting(jobPostingId);
        }

        public bool UpdateJobPosting(JobPosting jobPosting)
        {
            return _repo.UpdateJobPosting(jobPosting);
        }

        public bool WriteJson(JobPosting jobPosting)
        {
            return _repo.WriteJson(jobPosting);
        }
    }
}
