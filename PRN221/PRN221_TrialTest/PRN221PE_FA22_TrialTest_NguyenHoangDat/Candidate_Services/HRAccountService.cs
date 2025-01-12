using Candidate_BusinessObjects;
using Candidate_Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Candidate_Services
{
    public class HRAccountService : IHRAccountService
    {
        private IHRAccountRepo iAccountRepo = null;
        public HRAccountService()
        {
            if (iAccountRepo == null) 
                iAccountRepo = new HRAccountRepo();
        }
        public Hraccount GetHraccount(string email)
        {
            return iAccountRepo.GetHraccount(email);
        }

        public Hraccount Login(string email, string password)
        {
            Hraccount hraccount = GetHraccount(email);
            if (hraccount != null)
            {
                if (hraccount.Password == password)
                    return hraccount;
            }
            return null;
        }
    }
}
