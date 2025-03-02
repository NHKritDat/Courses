using CS.Repositories.Base;
using CS.Repositories.Models;
using Microsoft.EntityFrameworkCore;

namespace CS.Repositories
{
    public class SystemAccountRepository : GenericRepository<SystemAccount>
    {
        public SystemAccountRepository() { }
        public async Task<SystemAccount> GetSystemAccount(string email, string password) => await _context.SystemAccounts.FirstOrDefaultAsync(u => u.EmailAddress == email && u.AccountPassword == password);
    }
}
