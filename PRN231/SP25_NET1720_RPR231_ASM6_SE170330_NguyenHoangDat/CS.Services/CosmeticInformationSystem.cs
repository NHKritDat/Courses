using CS.Repositories;
using CS.Repositories.Models;

namespace CS.Services
{
    public interface ICosmeticInformationService
    {
        Task<int> CreateAsync(CosmeticInformation item);
        Task<List<CosmeticInformation>> GetAllAsync();
        Task<CosmeticInformation> GetByIdAsync(string id);
        Task<bool> RemoveAsync(string id);
        Task<List<CosmeticInformation>> Search(string name, string size, string type);
        Task<int> Update(CosmeticInformation item);
    }
    public class CosmeticInformationService : ICosmeticInformationService
    {
        private readonly CosmeticInformationRepository _cosmeticInformationRepository;
        public CosmeticInformationService() => _cosmeticInformationRepository = new CosmeticInformationRepository();
        public async Task<int> CreateAsync(CosmeticInformation item) => await _cosmeticInformationRepository.CreateAsync(item);

        public async Task<bool> RemoveAsync(string id)
        {
            var item = await _cosmeticInformationRepository.GetByIdAsync(id);
            if (item != null)
            {
                return await _cosmeticInformationRepository.RemoveAsync(item);
            }
            return false;
        }

        public async Task<List<CosmeticInformation>> GetAllAsync() => await _cosmeticInformationRepository.GetAllAsync();

        public async Task<CosmeticInformation> GetByIdAsync(string id) => await _cosmeticInformationRepository.GetByIdAsync(id);

        public async Task<List<CosmeticInformation>> Search(string name, string size, string type) => await _cosmeticInformationRepository.Search(name, size, type);

        public async Task<int> Update(CosmeticInformation item) => await _cosmeticInformationRepository.UpdateAsync(item);
    }
}
