using System.Threading.Tasks;
using BusinessObjects;
using Common;
using MassTransit;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Schedules.Microservices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SchedulesController : ControllerBase
    {
        private readonly IBus _bus;
        private readonly ILogger _logger;
        private static List<Schedule> schedules;

        public SchedulesController(ILogger<SchedulesController> logger, IBus bus)
        {
            _logger = logger;
            _bus = bus;
            if (schedules == null)
            {
                schedules = new();
                schedules.Add(new()
                {
                    Id = Guid.NewGuid(),
                    UserId = Guid.NewGuid(),
                    WorkDate = DateTime.Now,
                    Status = "Active",
                    CreatedAt = DateTime.Now,
                    UpdatedAt = DateTime.Now,
                    Title = "Test Schedule",
                    Description = "Test Description",
                    Location = "Test Location",
                    Notes = "Test Notes"
                });
                schedules.Add(new()
                {
                    Id = Guid.NewGuid(),
                    UserId = Guid.NewGuid(),
                    WorkDate = DateTime.Now,
                    Status = "Active",
                    CreatedAt = DateTime.Now,
                    UpdatedAt = DateTime.Now,
                    Title = "Test Schedule 2",
                    Description = "Test Description 2",
                    Location = "Test Location 2",
                    Notes = "Test Notes 2"
                });
                schedules.Add(new()
                {
                    Id = Guid.NewGuid(),
                    UserId = Guid.NewGuid(),
                    WorkDate = DateTime.Now,
                    Status = "Active",
                    CreatedAt = DateTime.Now,
                    UpdatedAt = DateTime.Now,
                    Title = "Test Schedule 3",
                    Description = "Test Description 3",
                    Location = "Test Location 3",
                    Notes = "Test Notes 3"
                });
            }
        }

        // GET: api/<SchedulesController>
        [HttpGet]
        public IEnumerable<Schedule> Get() => schedules;

        // GET api/<SchedulesController>/5
        [HttpGet("{id}")]
        public Schedule Get(Guid id) => schedules.FirstOrDefault(s => s.Id == id);

        // POST api/<SchedulesController>
        [HttpPost]
        public async Task<IActionResult> Post([FromBody] Schedule value)
        {
            if(value != null)
            {
                Uri uri = new("rabbitmq://localhost/scheduleQueue");
                var endPoint = await _bus.GetSendEndpoint(uri);
                await endPoint.Send(value);

                string messageLog = string.Format("[{0}] PUBLISH data to RabbitMQ.scheduleQueue: {1}", DateTime.Now, Utilities.ConvertToJsonString(value));
                _logger.LogInformation(messageLog);

                schedules.Add(value);
                return Ok();
            }
            return BadRequest();
        }

        // PUT api/<SchedulesController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/<SchedulesController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
