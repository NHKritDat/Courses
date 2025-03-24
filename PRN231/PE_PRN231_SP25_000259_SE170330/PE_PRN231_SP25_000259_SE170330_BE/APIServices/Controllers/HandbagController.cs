using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.OData.Query;
using Repositories.Models;
using Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace APIServices.Controllers
{
    [Authorize]
    [EnableQuery]
    [Route("api/[controller]")]
    [ApiController]
    public class HandbagController : ControllerBase
    {
        private readonly IHandbagService _handbagService;
        public HandbagController(IHandbagService handbagService) => _handbagService = handbagService;
        // GET: api/<HandbagController>
        [HttpGet]
        [Authorize(Roles = "2,3,4")]
        public async Task<IEnumerable<Handbag>> Get() => await _handbagService.GetAllAsync();

        // GET api/<HandbagController>/5
        [HttpGet("{id}")]
        [Authorize(Roles = "2,3,4")]
        public async Task<Handbag> Get(int id) => await _handbagService.GetByIdAsync(id);

        // POST api/<HandbagController>
        [HttpPost]
        [Authorize(Roles = "2")]
        public async Task<int> Post([FromBody] Handbag value) => await _handbagService.CreateAsync(value);

        // PUT api/<HandbagController>/5
        [HttpPut("{id}")]
        [Authorize(Roles = "2")]
        public async Task<int> Put(int id, [FromBody] Handbag value) => await _handbagService.UpdateAsync(value);

        // DELETE api/<HandbagController>/5
        [HttpDelete("{id}")]
        [Authorize(Roles = "2")]
        public async Task<bool> Delete(int id) => await _handbagService.RemoveAsync(id);
    }
}
