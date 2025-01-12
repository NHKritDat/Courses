using Candidate_BusinessObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Candidate_Daos
{
    public class JobPostingDao
    {
        private static JobPostingDao instance = null;
        private CandidateManagementContext context;
        public JobPostingDao()
        {
            context = new CandidateManagementContext();
        }
        public static JobPostingDao Instance
        {
            get
            {
                if (instance == null)
                    instance = new JobPostingDao();
                return instance;
            }
        }

        public bool AddJobPosting(JobPosting jobPosting)
        {
            bool isSuccess = false;
            try
            {
                context.Add(jobPosting);
                context.SaveChanges();
                isSuccess = true;
            }
            catch (Exception ex)
            {
                //Write log
            }
            return isSuccess;
        }

        public JobPosting GetJobPosting(string jobId)
        {
            return context.JobPostings.SingleOrDefault(m => m.PostingId.Equals(jobId));
        }

        public bool DeleteJobPosting(string jobId)
        {
            bool isSuccess = false;
            try
            {
                JobPosting job = GetJobPosting(jobId);
                if (job != null)
                {
                    context.JobPostings.Remove(job);
                    context.SaveChanges();
                    isSuccess = true;
                }
            }
            catch (Exception ex)
            {

            }
            return isSuccess;
        }

        public bool UpdateJobPosting(JobPosting jobPosting)
        {
            bool isSuccess = false;
            try
            {
                JobPosting job = GetJobPosting(jobPosting.PostingId);
                if (job != null)
                {
                    context.JobPostings.Update(jobPosting);
                    context.SaveChanges();
                    context.Entry<JobPosting>(jobPosting).State = Microsoft.EntityFrameworkCore.EntityState.Detached;
                    isSuccess = true;
                }
            }
            catch (Exception ex)
            {

            }
            return isSuccess;
        }

        public List<JobPosting> GetJobPostings()
        {
            return context.JobPostings.ToList();
        }

        public void WriteJson(string path)
        {
            CandidateUtils.JsonUtil<JobPosting>.WriteJson(context.JobPostings.ToList(), path);
        }
    }
}
