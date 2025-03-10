using Microsoft.AspNetCore.Mvc;
using SCBS.Repositories.Models;
using SCBS.Services;

namespace SCBS.BlazorApp.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SchedulesController : ControllerBase
    {
        private readonly IScheduleService _scheduleService;

        public SchedulesController(IScheduleService scheduleService)
        {
            _scheduleService = scheduleService;
        }

        // GET: api/Schedules
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Schedule>>> GetSchedules()
        {
            return await _scheduleService.GetAllAsync();
        }

        // GET: api/Schedules/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Schedule>> GetSchedule(Guid id)
        {
            var schedule = await _scheduleService.GetByIdAsync(id);

            if (schedule == null)
            {
                return NotFound();
            }

            return schedule;
        }

        // PUT: api/Schedules/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<int> PutSchedule(Guid id, Schedule schedule) => await _scheduleService.UpdateAsync(schedule);

        // POST: api/Schedules
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<int>> PostSchedule(Schedule schedule) => await _scheduleService.CreateAsync(schedule);

        // DELETE: api/Schedules/5
        [HttpDelete("{id}")]
        public async Task<bool> DeleteSchedule(Guid id) => await _scheduleService.RemoveAsync(id);

        private bool ScheduleExists(Guid id)
        {
            return _scheduleService.GetAllAsync().Result.Any(e => e.Id == id);
        }
    }
}
