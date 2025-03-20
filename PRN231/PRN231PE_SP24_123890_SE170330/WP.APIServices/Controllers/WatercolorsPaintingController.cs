using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.OData.Query;
using WP.Repositories.Models;
using WP.Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace WP.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    [EnableQuery]
    public class WatercolorsPaintingController : ControllerBase
    {
        private readonly IWatercolorsPaintingService _watercolorsPaintingService;
        public WatercolorsPaintingController(IWatercolorsPaintingService watercolorsPaintingService) => _watercolorsPaintingService = watercolorsPaintingService;

        // GET: api/<WatercolorsPaintingController>
        [HttpGet]
        [Authorize(Roles = "3,2")]
        public async Task<IEnumerable<WatercolorsPainting>> Get() => await _watercolorsPaintingService.GetAllAsync();

        // GET api/<WatercolorsPaintingController>/5
        [HttpGet("{id}")]
        [Authorize(Roles = "3,2")]
        public async Task<WatercolorsPainting> Get(string id) => await _watercolorsPaintingService.GetByIdAsync(id);

        // POST api/<WatercolorsPaintingController>
        [HttpPost]
        [Authorize(Roles = "3")]
        public async Task<int> Post([FromBody] WatercolorsPainting value) => await _watercolorsPaintingService.CreateAsync(value);

        // PUT api/<WatercolorsPaintingController>/5
        [HttpPut("{id}")]
        [Authorize(Roles = "3")]
        public async Task<int> Put(string id, [FromBody] WatercolorsPainting value) => await _watercolorsPaintingService.UpdateAsync(value);

        // DELETE api/<WatercolorsPaintingController>/5
        [HttpDelete("{id}")]
        [Authorize(Roles = "3")]
        public async Task<bool> Delete(string id) => await _watercolorsPaintingService.RemoveAsync(id);
    }
}
