using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using CosmeticsStore.Repositories.Base;
using CosmeticsStore.Repositories.Models;
using Microsoft.EntityFrameworkCore;

namespace CosmeticsStore.Repositories
{
 public   class SystemAccountRepository:GenericRepository<SystemAccount>
    {
        public SystemAccountRepository() { 
        }
        public async Task<SystemAccount> GetSystemAccount(string email, string password) => await _context.SystemAccounts.FirstOrDefaultAsync(u => u.EmailAddress == email && u.AccountPassword == password);
    }
}
