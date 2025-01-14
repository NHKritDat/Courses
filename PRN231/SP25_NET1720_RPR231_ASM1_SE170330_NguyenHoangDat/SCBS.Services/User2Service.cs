using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SCBS.Repositories;
using SCBS.Repositories.Models;

namespace SCBS.Services
{
    public interface IUser2Service
    {
        Task<List<User>> GetAll();
        Task<User> GetById(Guid id);
        Task<int> Create(User user);
        Task<int> Update(User user);
        Task<bool> Delete(Guid id);
        Task<List<User>> Search(string username, string email, string fullName);
    }
    public class User2Service : IUser2Service
    {
        private readonly UserRepository _userRepository;
        public User2Service()
        {
            _userRepository = new UserRepository();
        }

        public async Task<int> Create(User user)
        {
            return await _userRepository.CreateAsync(user);
        }

        public async Task<bool> Delete(Guid id)
        {
            var item = await _userRepository.GetByIdAsync(id);
            if (item != null)
            {
                return await _userRepository.RemoveAsync(item);
            }
            return false;
        }

        public Task<List<User>> GetAll()
        {
            return _userRepository.GetAllAsync();
        }

        public Task<User> GetById(Guid id)
        {
            return _userRepository.GetByIdAsync(id);
        }

        public Task<List<User>> Search(string username, string email, string fullName)
        {
            return _userRepository.Search(username, email, fullName);
        }

        public async Task<int> Update(User user)
        {
            return await _userRepository.UpdateAsync(user);
        }
    }
}
