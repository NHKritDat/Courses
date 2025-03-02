using CS.Repositories.Models;
using CS.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.OData.Query;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace CS.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    [EnableQuery]
    public class CosmeticInformationController : ControllerBase
    {
        private readonly ICosmeticInformationService _cosmeticInformationService;
        public CosmeticInformationController(ICosmeticInformationService cosmeticInformationService) => _cosmeticInformationService = cosmeticInformationService;
        // GET: api/<CosmeticInformationController>
        [HttpGet]
        [Authorize(Roles = "1,3,4")]
        public async Task<IEnumerable<CosmeticInformation>> Get() => await _cosmeticInformationService.GetAllAsync();

        [HttpGet("search")]
        [Authorize(Roles = "1,3,4")]
        public async Task<IEnumerable<CosmeticInformation>> Search([FromQuery] string? name, [FromQuery] string? size, [FromQuery] string? type) => await _cosmeticInformationService.Search(name, size, type);

        // GET api/<CosmeticInformationController>/5
        [HttpGet("{id}")]
        [Authorize(Roles = "1,3,4")]
        public async Task<CosmeticInformation> Get(string id) => await _cosmeticInformationService.GetByIdAsync(id);

        // POST api/<CosmeticInformationController>
        [HttpPost]
        [Authorize(Roles = "1")]
        public async Task<int> Post([FromBody] CosmeticInformation cosmeticInformation) => await _cosmeticInformationService.CreateAsync(cosmeticInformation);

        // PUT api/<CosmeticInformationController>
        [HttpPut]
        [Authorize(Roles = "1")]
        public async Task<int> Put([FromBody] CosmeticInformation cosmeticInformation) => await _cosmeticInformationService.Update(cosmeticInformation);

        // DELETE api/<CosmeticInformationController>/5
        [HttpDelete("{id}")]
        [Authorize(Roles = "1")]
        public async Task<bool> Delete(string id) => await _cosmeticInformationService.RemoveAsync(id);
    }
}
