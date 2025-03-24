using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repositories;
using Repositories.Models;

namespace Services
{
    public interface IHandbagService
    {
        Task<int> CreateAsync(Handbag item);
        Task<List<Handbag>> GetAllAsync();
        Task<Handbag> GetByIdAsync(int id);
        Task<bool> RemoveAsync(int id);
        Task<int> UpdateAsync(Handbag item);
    }
    public class HandbagService : IHandbagService
    {
        private readonly HandbagRepository _handbagRepository;
        public HandbagService() => _handbagRepository = new HandbagRepository();
        public async Task<int> CreateAsync(Handbag item) => await _handbagRepository.CreateAsync(item);

        public async Task<bool> RemoveAsync(int id)
        {
            var item = await _handbagRepository.GetByIdAsync(id);
            if (item != null)
            {
                return await _handbagRepository.RemoveAsync(item);
            }
            return false;
        }

        public async Task<List<Handbag>> GetAllAsync() => await _handbagRepository.GetAllAsync();

        public async Task<Handbag> GetByIdAsync(int id) => await _handbagRepository.GetByIdAsync(id);

        public async Task<int> UpdateAsync(Handbag item) => await _handbagRepository.UpdateAsync(item);

    }
}
