using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SCBS.Repositories;
using SCBS.Repositories.Models;

namespace SCBS.Services
{
    public interface IUserAccountService
    {
        Task<UserAccount> Authenticate(string username, string password);
    }
    public class UserAccountService : IUserAccountService
    {
        private readonly UserAccountRepository _userAccountRepository;
        public UserAccountService() => _userAccountRepository = new UserAccountRepository();
        public async Task<UserAccount> Authenticate(string username, string password) => await _userAccountRepository.GetUserAccount(username, password);
    }
}
