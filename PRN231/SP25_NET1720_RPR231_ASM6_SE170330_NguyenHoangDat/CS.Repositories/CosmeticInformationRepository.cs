using CS.Repositories.Base;
using CS.Repositories.Models;
using Microsoft.EntityFrameworkCore;

namespace CS.Repositories
{
    public class CosmeticInformationRepository : GenericRepository<CosmeticInformation>
    {
        public CosmeticInformationRepository() { }
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
        public async Task<List<CosmeticInformation>> Search(string name, string size, string type) => await _context.CosmeticInformations
                .Include(item => item.Category)
                .Where(item =>
                (string.IsNullOrEmpty(name) || item.CosmeticName.Contains(name))
                            && (string.IsNullOrEmpty(size) || item.CosmeticSize.Contains(size))
                            && (string.IsNullOrEmpty(type) || item.SkinType.Contains(type))
                )
                .ToListAsync();
    }
}
