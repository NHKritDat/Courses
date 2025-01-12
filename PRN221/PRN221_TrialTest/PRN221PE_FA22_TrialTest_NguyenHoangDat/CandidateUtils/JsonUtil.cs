using System.Text.Json;

namespace CandidateUtils
{
    public class JsonUtil<T>
    {
        private static JsonUtil<T> Instance = null;

        public JsonUtil()
        {
            if (Instance == null)
                Instance = new JsonUtil<T>();
        }

        public static void WriteJson(List<T> t, string path)
        {
            JsonSerializerOptions options = new JsonSerializerOptions()
            {
                WriteIndented = true,
                ReferenceHandler = System.Text.Json.Serialization.ReferenceHandler.IgnoreCycles
            };
            string json = JsonSerializer.Serialize(t, options);
            File.WriteAllText(path, json);
        }
    }
}
