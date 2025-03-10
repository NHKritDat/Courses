using Microsoft.EntityFrameworkCore;
using SCBS._SCBS.Repositories.Base;
using SCBS._SCBS.Repositories.Models;

namespace SCBS._SCBS.Repositories
{
    public class UserAccountRepository : GenericRepository<UserAccount>
    {
        public UserAccountRepository() { }
        public async Task<UserAccount> GetUserAccount(string userName, string password) => await _context.UserAccounts.FirstOrDefaultAsync(u => u.UserName == userName && u.Password == password && u.IsActive);

    }
}
