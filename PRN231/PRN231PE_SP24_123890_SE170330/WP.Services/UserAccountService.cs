using WP.Repositories;
using WP.Repositories.Models;

namespace WP.Services
{
    public interface IUserAccountService
    {
        Task<UserAccount> Authenticate(string email, string password);
    }
    public class UserAccountService : IUserAccountService
    {
        private readonly UserAccountRepository _userAccountRepository;
        public UserAccountService() => _userAccountRepository = new UserAccountRepository();
        public async Task<UserAccount> Authenticate(string email, string password) => await _userAccountRepository.GetUserAccount(email, password);
    }
}
