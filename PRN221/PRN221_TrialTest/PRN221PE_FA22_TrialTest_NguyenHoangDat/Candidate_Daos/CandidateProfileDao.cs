using Candidate_BusinessObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Candidate_Daos
{
    public class CandidateProfileDao
    {
        private static CandidateProfileDao instance;
        private CandidateManagementContext context;
        public CandidateProfileDao()
        {
            context = new CandidateManagementContext();
        }
        public static CandidateProfileDao Instance
        {
            get
            {
                if (instance == null)
                    instance = new CandidateProfileDao();
                return instance;
            }
        }
        public List<CandidateProfile> GetCandidateProfiles() => context.CandidateProfiles.ToList();

        public CandidateProfile GetCandidateProfile(string id) => context.CandidateProfiles.SingleOrDefault(c => c.CandidateId.Equals(id));

        public bool AddCandidateProfile(CandidateProfile candidateProfile)
        {
            bool isSuccess = false;
            try
            {
                context.Add(candidateProfile);
                context.SaveChanges();
                isSuccess = true;
            } catch (Exception ex)
            {
                isSuccess = false;
            }
            return isSuccess;
        }

        public bool DeleteCandidateProfile(string id)
        {
            bool isSuccess = false;
            try
            {
                CandidateProfile candidateProfile = GetCandidateProfile(id);
                if (candidateProfile != null)
                {
                    context.CandidateProfiles.Remove(candidateProfile);
                    context.SaveChanges();
                    isSuccess = true;
                }
            } catch (Exception ex)
            {
                isSuccess = false;
            }
            return isSuccess;
        }

        public bool UpdateCandidateProfile(CandidateProfile candidateProfile)
        {
            bool isSuccess = false;
            try
            {
                CandidateProfile candidate = GetCandidateProfile(candidateProfile.CandidateId);
                if (candidate != null)
                {
                    context.CandidateProfiles.Update(candidateProfile);
                    context.SaveChanges();
                    isSuccess = true;
                }
            } catch(Exception ex)
            {
                isSuccess = false;
            }
            return isSuccess;
        }

    }
}
