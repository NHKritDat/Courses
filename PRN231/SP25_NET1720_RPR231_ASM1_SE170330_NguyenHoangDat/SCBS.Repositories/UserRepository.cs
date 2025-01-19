using Microsoft.EntityFrameworkCore;
using SCBS.Repositories.Base;
using SCBS.Repositories.Models;

namespace SCBS.Repositories
{
    public class UserRepository : GenericRepository<User>
    {
        public UserRepository() { }

        public new async Task<List<User>> GetAllAsync()
        {
            var users = await _context.Users.Include(u => u.Schedules).ToListAsync();
            return users;
        }

        public new async Task<User?> GetByIdAsync(Guid id) => await _context.Users.Include(u => u.Schedules).FirstOrDefaultAsync(u => u.Id == id);

        public async Task<List<User>> Search(string username, string fullName, string email)
        {
            var users = await _context.Users
                .Where(u => (string.IsNullOrEmpty(username) || u.Username == username)
                            && (string.IsNullOrEmpty(fullName) || u.FullName == fullName)
                            && (string.IsNullOrEmpty(email) || u.Email == email))
                .Include(u => u.Schedules)
                .ToListAsync();
            return users;
        }
    }
}
