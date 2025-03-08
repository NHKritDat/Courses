using SCBS.GrapQLAPIServices.GrapQLs;
using SCBS.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddScoped<IScheduleService, ScheduleService>();
builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddGraphQLServer().AddQueryType<Query>().BindRuntimeType<DateTime, DateTimeType>();
builder.Services.AddGraphQLServer().AddMutationType<Mutation>().BindRuntimeType<DateTime, DateTimeType>();

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

app.UseCors(options => options.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader());
app.UseRouting().UseEndpoints(endpoints => { endpoints.MapGraphQL(); });

app.Run();
