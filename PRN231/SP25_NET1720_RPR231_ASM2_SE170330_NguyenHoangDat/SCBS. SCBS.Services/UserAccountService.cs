using SCBS._SCBS.Repositories;
using SCBS._SCBS.Repositories.Models;

namespace SCBS._SCBS.Services
{
    public interface IUserAccountService
    {
        Task<UserAccount> Authenticate(string userName, string password);
    }
    public class UserAccountService : IUserAccountService
    {
        private readonly UserAccountRepository _userAccountRepository;
        public UserAccountService() => _userAccountRepository = new UserAccountRepository();
        public async Task<UserAccount> Authenticate(string userName, string password) => await _userAccountRepository.GetUserAccount(userName, password);
    }

}
