using Candidate_BusinessObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Candidate_Services
{
    public interface ICandidateProfileService
    {
        public List<CandidateProfile> GetCandidates();
        public CandidateProfile GetCandidate(string id);
        public bool AddCandidateProfile(CandidateProfile profile);
        public bool RemoveCandidateProfile(string id);
        public bool UpdateCandidateProfile(CandidateProfile profile);
    }
}
