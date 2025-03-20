using Microsoft.EntityFrameworkCore;
using WP.Repositories.Base;
using WP.Repositories.Models;

namespace WP.Repositories
{
    public class UserAccountRepository : GenericRepository<UserAccount>
    {
        public UserAccountRepository() { }
        public async Task<UserAccount> GetUserAccount(string email, string password) => await _context.UserAccounts.FirstOrDefaultAsync(u => u.UserEmail == email && u.UserPassword == password);
    }
}
