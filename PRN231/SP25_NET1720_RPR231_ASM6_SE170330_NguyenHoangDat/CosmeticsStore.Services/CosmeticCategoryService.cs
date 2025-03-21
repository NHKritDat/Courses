using CosmeticsStore.Repositories;
using CosmeticsStore.Repositories.Models;

namespace CosmeticsStore.Services
{
    public interface ICosmeticCategoryService
    {
        Task<List<CosmeticCategory>> GetAllAsync();
    }
    public class CosmeticCategoryService : ICosmeticCategoryService
    {
        private readonly CosmeticCategoryRepository _cosmeticCategoryRepository;
        public CosmeticCategoryService() => _cosmeticCategoryRepository = new CosmeticCategoryRepository();
        public async Task<List<CosmeticCategory>> GetAllAsync() => await _cosmeticCategoryRepository.GetAllAsync();
    }

}
