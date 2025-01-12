using Candidate_BusinessObjects;
using Candidate_Daos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using System.Xml;

namespace Candidate_Repositories
{
    public class JobPostingRepo : IJobPostingRepo
    {
        public bool AddJobPosting(JobPosting jobPosting) => JobPostingDao.Instance.AddJobPosting(jobPosting);

        public JobPosting GetJobPosting(string jobId) => JobPostingDao.Instance.GetJobPosting(jobId);

        public List<JobPosting> GetJobPosting() => JobPostingDao.Instance.GetJobPostings();

        public bool RemoveJobPosting(string jobPostingId) => JobPostingDao.Instance.DeleteJobPosting(jobPostingId);

        public bool UpdateJobPosting(JobPosting jobPosting) => JobPostingDao.Instance.UpdateJobPosting(jobPosting);

        public bool WriteJson(JobPosting jobPosting)
        {
            string json = File.ReadAllText("job.json");
            List<JobPosting>? jobPostings = new List<JobPosting>();
            if (json != null)
                jobPostings = JsonSerializer.Deserialize<List<JobPosting>>(json);
            if (jobPostings == null)
                jobPostings = new List<JobPosting>();
            jobPostings.Add(jobPosting);
            JsonSerializerOptions options = new JsonSerializerOptions();
            options.WriteIndented = true;
            string newJson = JsonSerializer.Serialize(jobPostings, options);
            File.WriteAllText("job.json", newJson);
            return true;
        }
    }
}
