using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SCBS.Repositories;
using SCBS.Repositories.Models;

namespace SCBS.Services
{
    public interface IUserAccount2Service
    {
        Task<List<UserAccount>> GetAll();
        Task<UserAccount> GetById(string id);
        Task<int> Create(UserAccount userAccount);
        Task<int> Update(UserAccount userAccount);
        Task<bool> Delete(int id);
        Task<UserAccount> GetUserAccount(string username, string password);
    }
    public class UserAccount2Service : IUserAccount2Service
    {
        private readonly UserAccountRepository _userAccountRepository;
        public UserAccount2Service()
        {
            _userAccountRepository = new UserAccountRepository();
        }

        public async Task<int> Create(UserAccount userAccount)
        {
            return await _userAccountRepository.CreateAsync(userAccount);
        }

        public async Task<bool> Delete(int id)
        {
            var item = await _userAccountRepository.GetByIdAsync(id);
            if (item != null)
            {
                return await _userAccountRepository.RemoveAsync(item);
            }
            return false;
        }

        public Task<List<UserAccount>> GetAll()
        {
            return _userAccountRepository.GetAllAsync();
        }

        public Task<UserAccount> GetById(string id)
        {
            return _userAccountRepository.GetByIdAsync(id);
        }

        public Task<UserAccount?> GetUserAccount(string username, string password)
        {
            return _userAccountRepository.GetUserAccount(username, password);
        }

        public async Task<int> Update(UserAccount userAccount)
        {
            return await _userAccountRepository.UpdateAsync(userAccount);
        }
    }
}
