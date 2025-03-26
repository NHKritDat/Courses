using MassTransit;
using Users.Microservices.Consumer;

var builder = WebApplication.CreateBuilder(args);

////Logger
builder.Host.ConfigureLogging(logging =>
{
    logging.ClearProviders();
    logging.AddConsole();
});

////RabbitMQ-Publisher: Adds the MassTransit Service to the ASP.NET Core Service Container
builder.Services.AddMassTransit(x =>
{
    x.AddConsumer<ScheduleConsumer>();
    //Creates a new Service Bus using RabbitMQ, pass paramteres like the host url, username and password.
    x.AddBus(provider => Bus.Factory.CreateUsingRabbitMq(cfg =>
    {
        //cfg.UseHealthCheck(provider);
        //cfg.Host(new Uri("rabbitmq://localhost:xxxx"), h =>
        cfg.Host(new Uri("rabbitmq://localhost"), h =>
        {
            h.Username("guest");
            h.Password("guest");
        });
        //define the Receive endpoint, since this is a consumer.
        cfg.ReceiveEndpoint("scheduleQueue", ep =>
        {
            ep.PrefetchCount = 16;
            ep.UseMessageRetry(r => r.Interval(2, 100));
            //Finally, link the orderQueue to the OrderConsumer class.
            ep.ConfigureConsumer<ScheduleConsumer>(provider);
        });
    }));
});

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
