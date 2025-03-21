using CosmeticsStore.Repositories.Base;
using CosmeticsStore.Repositories.Models;
using Microsoft.EntityFrameworkCore;

namespace CosmeticsStore.Repositories
{
    public class CosmeticInformationRepository : GenericRepository<CosmeticInformation>
    {
        public CosmeticInformationRepository()
        {
        }
        public async Task<List<CosmeticInformation>> GetAllAsync() => await _context.CosmeticInformations.Include(item => item.Category).ToListAsync();
        public async Task<CosmeticInformation> GetByIdAsync(string id)
        {
            var entity = await _context.CosmeticInformations.Include(item => item.Category).FirstOrDefaultAsync(item => item.CosmeticId == id);
            if (entity != null)
            {
                _context.Entry(entity).State = EntityState.Detached;
            }
            return entity;
        }

    }
}
