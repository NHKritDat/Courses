using WP.Repositories;
using WP.Repositories.Models;

namespace WP.Services
{
    public interface IWatercolorsPaintingService
    {
        Task<int> CreateAsync(WatercolorsPainting item);
        Task<List<WatercolorsPainting>> GetAllAsync();
        Task<WatercolorsPainting> GetByIdAsync(string id);
        Task<bool> RemoveAsync(string id);
        Task<int> UpdateAsync(WatercolorsPainting item);
    }
    public class WatercolorsPaintingService : IWatercolorsPaintingService
    {
        private readonly WatercolorsPaintingRepository _watercolorsPaintingRepository;
        public WatercolorsPaintingService() => _watercolorsPaintingRepository = new WatercolorsPaintingRepository();
        public async Task<int> CreateAsync(WatercolorsPainting item) => await _watercolorsPaintingRepository.CreateAsync(item);

        public async Task<bool> RemoveAsync(string id)
        {
            var item = await _watercolorsPaintingRepository.GetByIdAsync(id);
            if (item != null)
            {
                return await _watercolorsPaintingRepository.RemoveAsync(item);
            }
            return false;
        }

        public async Task<List<WatercolorsPainting>> GetAllAsync() => await _watercolorsPaintingRepository.GetAllAsync();

        public async Task<WatercolorsPainting> GetByIdAsync(string id) => await _watercolorsPaintingRepository.GetByIdAsync(id);


        public async Task<int> UpdateAsync(WatercolorsPainting item) => await _watercolorsPaintingRepository.UpdateAsync(item);
    }

}
