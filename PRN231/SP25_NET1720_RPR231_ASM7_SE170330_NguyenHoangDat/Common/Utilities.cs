using System;
using System.IO;
using System.Text.Json;
using System.Threading.Tasks;

namespace Common
{
    public class Utilities
    {
        public static string ConvertToJsonString<T>(T obj)
        {
            try
            {
                var options = new JsonSerializerOptions { WriteIndented = true };
                return JsonSerializer.Serialize(obj, options);
            }
            catch (Exception ex)
            {
                // Handle or log the exception as needed
                Console.WriteLine($"An error occurred while converting to JSON string: {ex.Message}");
                return string.Empty;
            }
        }
    }
}
