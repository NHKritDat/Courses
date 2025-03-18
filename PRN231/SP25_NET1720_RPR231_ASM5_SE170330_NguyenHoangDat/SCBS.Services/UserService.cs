using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SCBS.Repositories;
using SCBS.Repositories.Models;

namespace SCBS.Services
{
    public interface IUserService
    {
        Task<int> CreateAsync(User user);
        Task<bool> DeleteAsync(Guid id);
        Task<List<User>> GetAllAsync();
        Task<User> GetByIdAsync(Guid id);
        Task<int> UpdateAsync(User user);
    }
    public class UserService : IUserService
    {
        private readonly UserRepository _userRepository;
        public UserService() => _userRepository = new UserRepository();
        public async Task<List<User>> GetAllAsync() => await _userRepository.GetAllAsync();

        public async Task<User> GetByIdAsync(Guid id) => await _userRepository.GetByIdAsync(id);
        public async Task<int> CreateAsync(User user) => await _userRepository.CreateAsync(user);
        public async Task<int> UpdateAsync(User user) => await _userRepository.UpdateAsync(user);
        public async Task<bool> DeleteAsync(Guid id)
        {
            var user = await GetByIdAsync(id);
            if (user == null) return false;
            return await _userRepository.RemoveAsync(user);
        }
    }
}
