using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.OData.Query;
using SCBS.Repositories.Models;
using SCBS.Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace SCBS.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    [EnableQuery]
    public class ScheduleController : ControllerBase
    {
        private readonly IScheduleService _scheduleService;
        public ScheduleController(IScheduleService scheduleService) => _scheduleService = scheduleService;

        // GET: api/<ScheduleController>
        [HttpGet]
        [Authorize(Roles = "1,2")]
        public async Task<IEnumerable<Schedule>> Get() => await _scheduleService.GetAllAsync();

        // GET api/<ScheduleController>/5
        [HttpGet("{id}")]
        [Authorize(Roles = "1,2")]
        public async Task<Schedule?> Get(Guid id) => await _scheduleService.GetByIdAsync(id);

        [HttpGet("{workDate}/{status}")]
        [Authorize(Roles = "1,2")]
        public async Task<IEnumerable<Schedule>> Get(DateTime workDate, string status) => await _scheduleService.Search(workDate, status);

        // POST api/<ScheduleController>
        [HttpPost]
        [Authorize(Roles = "1,2")]
        public async Task<int> Post(Schedule schedule)
        {
            return await _scheduleService.Create(schedule);
        }

        // PUT api/<ScheduleController>/5
        [HttpPut("{id}")]
        [Authorize(Roles = "1,2")]
        public async Task<int> Put(Schedule schedule) => await _scheduleService.Update(schedule);

        // DELETE api/<ScheduleController>/5
        [HttpDelete("{id}")]
        [Authorize(Roles = "1")]
        public async Task<bool> Delete(Guid id) => await _scheduleService.Delete(id);
    }
}
