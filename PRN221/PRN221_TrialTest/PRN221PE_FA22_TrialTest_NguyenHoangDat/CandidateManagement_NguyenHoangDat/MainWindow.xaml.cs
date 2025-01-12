using Candidate_BusinessObjects;
using Candidate_Services;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace CandidateManagement_NguyenHoangDat
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private IHRAccountService iAccountService=null;
        public MainWindow()
        {
            InitializeComponent();
            if (iAccountService == null) 
                iAccountService = new HRAccountService();
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            MessageBoxResult answer = MessageBox.Show("Do you want to exit the app?", "Exit App!", MessageBoxButton.YesNo, MessageBoxImage.Question);
            if (answer == MessageBoxResult.Yes)
                Application.Current.Shutdown();
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            if (string.IsNullOrEmpty(txtEmail.Text) || string.IsNullOrEmpty(txtPassword.Password))
            {
                MessageBox.Show("Please enter email and password!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            Hraccount hraccount = iAccountService.Login(txtEmail.Text, txtPassword.Password);

            if (hraccount == null)
            {
                MessageBox.Show("Login failed! Please check your email or password.", "Login Failed", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            if (hraccount.MemberRole != 1)
            {
                MessageBox.Show("You do not have permission to access this function!", "Access Denied", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            this.Hide();

            JobPostingWindown jobPostingWindow = new JobPostingWindown();
            jobPostingWindow.ShowDialog();

            CandidateProfileWindow candidateProfileWindow = new CandidateProfileWindow();
            candidateProfileWindow.ShowDialog();

            this.Close();
        }
    }
}