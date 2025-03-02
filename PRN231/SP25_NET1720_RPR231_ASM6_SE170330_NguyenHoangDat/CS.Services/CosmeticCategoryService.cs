using CS.Repositories;
using CS.Repositories.Models;

namespace CS.Services
{
    public interface ICosmeticCategoryService
    {
        Task<int> CreateAsync(CosmeticCategory item);
        Task<List<CosmeticCategory>> GetAllAsync();
        Task<CosmeticCategory> GetByIdAsync(string id);
        Task<bool> RemoveAsync(string id);
        Task<int> Update(CosmeticCategory item);
    }
    public class CosmeticCategoryService : ICosmeticCategoryService
    {
        private readonly CosmeticCategoryRepository _cosmeticCategoryRepository;
        public CosmeticCategoryService() => _cosmeticCategoryRepository = new CosmeticCategoryRepository();
        public async Task<int> CreateAsync(CosmeticCategory item) => await _cosmeticCategoryRepository.CreateAsync(item);

        public async Task<bool> RemoveAsync(string id)
        {
            var item = await _cosmeticCategoryRepository.GetByIdAsync(id);
            if (item != null)
            {
                return await _cosmeticCategoryRepository.RemoveAsync(item);
            }
            return false;
        }

        public async Task<List<CosmeticCategory>> GetAllAsync() => await _cosmeticCategoryRepository.GetAllAsync();

        public async Task<CosmeticCategory> GetByIdAsync(string id) => await _cosmeticCategoryRepository.GetByIdAsync(id);

        public async Task<int> Update(CosmeticCategory item) => await _cosmeticCategoryRepository.UpdateAsync(item);
    }
}
