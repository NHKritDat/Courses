using SCBS.Repositories;
using SCBS.Repositories.Models;

namespace SCBS.Services
{
    public interface IUserService
    {
        Task<List<User>> GetAll();
        Task<User?> GetById(Guid id);
        Task<int> Create(User user);
        Task<int> Update(User user);
        Task<bool> Delete(Guid id);
        Task<List<User>> Search(string username, string email, string fullName);
    }
    public class UserService : IUserService
    {
        private readonly UserRepository _userRepository;
        public UserService() => _userRepository = new UserRepository();

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

        public async Task<List<User>> GetAll()
        {
            return await _userRepository.GetAllAsync();
        }

        public async Task<User?> GetById(Guid id) => await _userRepository.GetByIdAsync(id);

        public async Task<List<User>> Search(string username, string email, string fullName)
        {
            return await _userRepository.Search(username, email, fullName);
        }

        public async Task<int> Update(User user)
        {
            return await _userRepository.UpdateAsync(user);
        }
    }
}
