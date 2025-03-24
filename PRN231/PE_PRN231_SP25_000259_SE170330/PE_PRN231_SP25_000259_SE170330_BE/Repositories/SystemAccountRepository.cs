using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Repositories.Base;
using Repositories.Models;

namespace Repositories
{
    public class SystemAccountRepository :GenericRepository<SystemAccount>
    {
        public SystemAccountRepository() { }
        public async Task<SystemAccount> GetSystemAccount(string email, string password) => await _context.SystemAccounts.FirstOrDefaultAsync(u => u.Email == email && u.Password == password && u.IsActive == true);

    }
}
