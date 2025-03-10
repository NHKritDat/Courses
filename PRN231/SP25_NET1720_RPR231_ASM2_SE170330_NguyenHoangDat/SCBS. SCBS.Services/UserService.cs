using SCBS._SCBS.Repositories;
using SCBS._SCBS.Repositories.Models;

namespace SCBS._SCBS.Services
{
    public interface IUserService
    {
        Task<List<User>> GetAllAsync();
        Task<User> GetByIdAsync(Guid id);
    }
    public class UserService : IUserService
    {
        private readonly UserRepository _userRepository;
        public UserService() => _userRepository = new UserRepository();
        public async Task<List<User>> GetAllAsync() => await _userRepository.GetAllAsync();

        public async Task<User> GetByIdAsync(Guid id) => await _userRepository.GetByIdAsync(id);

    }

}
