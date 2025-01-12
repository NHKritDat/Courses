using Candidate_BusinessObjects;
using Candidate_Daos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Candidate_Repositories
{
    public class HRAccountRepo:IHRAccountRepo
    {
        public Hraccount GetHraccount(string email) => HRAccountDAO.Instance.GetHraccount(email);

    }
}
