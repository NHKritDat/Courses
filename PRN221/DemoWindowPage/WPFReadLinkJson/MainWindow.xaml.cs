using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace WPFReadLinkJson
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private async void DataGrid_Loaded(object sender, RoutedEventArgs e)
        {
            using HttpClient client = new HttpClient();
            var response = await client.GetAsync("https://jsonplaceholder.typicode.com/posts");
            var responseString = await response.Content.ReadAsStringAsync();
            var list = JsonSerializer.Deserialize<List<Post>>(responseString);
            dtgView.ItemsSource = list;
        }

        public class Post
        {
            public int userId { get; set; }
            public int id { get; set; }
            public string? title { get; set; }
            public string? body { get; set; }
        }
    }
}