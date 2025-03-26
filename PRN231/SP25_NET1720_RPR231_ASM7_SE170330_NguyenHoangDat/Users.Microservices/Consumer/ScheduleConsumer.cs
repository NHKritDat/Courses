using BusinessObjects;
using Common;
using MassTransit;

namespace Users.Microservices.Consumer
{
    public class ScheduleConsumer : IConsumer<Schedule>
    {
        private readonly ILogger _logger;

        public ScheduleConsumer(ILogger<ScheduleConsumer> logger)
        {
            _logger = logger;
        }

        public Task Consume(ConsumeContext<Schedule> context)
        {
            var data = context.Message;

            #region Business rule process anh/or call other API Service

            //Validate the Data
            //Store to Database
            //Notify the user via Email / SMS

            #endregion
            if (data != null)
            {
                string messageLog = string.Format("[{0}] RECEIVE data from RabbitMQ.scheduleQueue: {1}", DateTime.Now.ToString(), Utilities.ConvertToJsonString(data));
                _logger.LogInformation(messageLog);

            }

            return Task.CompletedTask;
        }
    }
}
