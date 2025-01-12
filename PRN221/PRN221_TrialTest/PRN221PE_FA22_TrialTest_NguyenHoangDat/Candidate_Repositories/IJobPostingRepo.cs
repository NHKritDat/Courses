using Candidate_BusinessObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Candidate_Repositories
{
    public interface IJobPostingRepo
    {
        public bool AddJobPosting(JobPosting jobPosting);
        public bool RemoveJobPosting(string jobPostingId);
        public bool UpdateJobPosting(JobPosting jobPosting);
        public JobPosting GetJobPosting(string jobId);
        public List<JobPosting> GetJobPosting();
        public bool WriteJson(JobPosting jobPosting);
    }
}
