using Candidate_BusinessObjects;
using Candidate_Daos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Candidate_Repositories
{
    public class CandidateProfileRepo : ICandidateProfileRepo
    {
        public bool AddCandidateProfile(CandidateProfile profile) => CandidateProfileDao.Instance.AddCandidateProfile(profile);

        public CandidateProfile GetProfile(string id) => CandidateProfileDao.Instance.GetCandidateProfile(id);

        public List<CandidateProfile> GetProfiles() => CandidateProfileDao.Instance.GetCandidateProfiles();

        public bool RemoveCandidateProfile(string id) => CandidateProfileDao.Instance.DeleteCandidateProfile(id);

        public bool UpdateCandidateProfile(CandidateProfile profile) => CandidateProfileDao.Instance.UpdateCandidateProfile(profile);
    }
}
