using WP.Repositories;
using WP.Repositories.Models;

namespace WP.Services
{
    public interface IStyleService
    {
        Task<List<Style>> GetAllAsync();
    }
    public class StyleService : IStyleService
    {
        private readonly StyleRepository _styleRepository;
        public StyleService() => _styleRepository = new StyleRepository();
        public async Task<List<Style>> GetAllAsync() => await _styleRepository.GetAllAsync();
    }
}
