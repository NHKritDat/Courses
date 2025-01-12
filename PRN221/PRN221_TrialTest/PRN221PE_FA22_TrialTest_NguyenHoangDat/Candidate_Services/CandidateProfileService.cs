using Candidate_BusinessObjects;
using Candidate_Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Candidate_Services
{
    public class CandidateProfileService : ICandidateProfileService
    {
        private readonly ICandidateProfileRepo candidateProfileRepo;
        public CandidateProfileService()
        {
            candidateProfileRepo = new CandidateProfileRepo();
        }

        public bool AddCandidateProfile(CandidateProfile profile) => candidateProfileRepo.AddCandidateProfile(profile);

        public CandidateProfile GetCandidate(string id) => candidateProfileRepo.GetProfile(id);

        public List<CandidateProfile> GetCandidates() => candidateProfileRepo.GetProfiles();

        public bool RemoveCandidateProfile(string id) => candidateProfileRepo.RemoveCandidateProfile(id);

        public bool UpdateCandidateProfile(CandidateProfile profile) => candidateProfileRepo.UpdateCandidateProfile(profile);
    }
}
