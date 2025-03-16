using Grpc.Net.Client;
using Grpc.Net.Client.Web;
using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.Components.WebAssembly.Hosting;
using SCBS.BlazorApp.GrpcClient;
using SCBS.BlazorApp.GrpcClient.Protos;

var builder = WebAssemblyHostBuilder.CreateDefault(args);
builder.RootComponents.Add<App>("#app");
builder.RootComponents.Add<HeadOutlet>("head::after");

builder.Services.AddScoped(sp => new HttpClient { BaseAddress = new Uri(builder.HostEnvironment.BaseAddress) });

builder.Services.AddGrpcClient<ScheduleGrpc.ScheduleGrpcClient>(options =>
{
    options.Address = new Uri("https://localhost:7264");
}).ConfigurePrimaryHttpMessageHandler(
        () => new GrpcWebHandler(new HttpClientHandler()));
builder.Services.AddGrpcClient<UserGrpc.UserGrpcClient>(options =>
{
    options.Address = new Uri("https://localhost:7264");
}).ConfigurePrimaryHttpMessageHandler(
        () => new GrpcWebHandler(new HttpClientHandler()));

await builder.Build().RunAsync();
