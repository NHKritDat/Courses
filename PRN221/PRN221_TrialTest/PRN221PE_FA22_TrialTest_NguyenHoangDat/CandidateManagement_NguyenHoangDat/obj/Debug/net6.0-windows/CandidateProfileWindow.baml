   M S B A M L   `   `   `  ����]  YCandidateManagement_NguyenHoangDat, Version=1.0.0.0, Culture=neutral, PublicKeyToken=nullW0clr-namespace:CandidateManagement_NguyenHoangDat"CandidateManagement_NguyenHoangDat  5   	   ?    9CandidateManagement_NguyenHoangDat.CandidateProfileWindow   5   	   W SPresentationCore, Version=6.0.2.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=6.0.2.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=6.0.2.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=6.0.2.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=6.0.2.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   Blocal0clr-namespace:CandidateManagement_NguyenHoangDat     5   	     � Title$  CandidateProfileWindow��5   	   $	��450��6(   $	��800��65    � WindowStyle$
 None=�6A    � WindowStartupLocation$ CenterScreen=�6T   .��5	      � .+�5
   
   �� $"��CANDIDATE PROFILE MANAGEMENT��6   $��186,10,186,380q�67   $
��Bold"�6O   $��24(�6a   �� 5   
   $��Candidate ID��6   $
��Left=�6'   $��	10,59,0,0q�6B   $	��Top=�6U   �� 5   
   $��FullName��6   $
��Left=�6#   $��	10,90,0,0q�6>   $	��Top=�6Q   �� 5   
   $��ImageURL��6   $
��Left=�6#   $��
10,121,0,0q�6>   $	��Top=�6R   �� 5   
   $��BirthDay��6   $
��Left=�6#   $��
10,152,0,0q�6>   $	��Top=�6R   �� 5   
   $��
JobPosting��6   $
��Left=�6%   $��
400,59,0,0q�6@   $	��Top=�6T   �� 5   
   $��Description��6   $
��Left=�6&   $��
400,90,0,0q�6A   $	��Top=�6U   �� 5   
   $
��Left=�6   $��57��6-   $��400,121,0,0q�69    �� TextWrapping$
 Wrap=�6N   $��TextBox��6b   $	��Top=�6q   $	��390��6�   �� 5   
   -   5         ��Name$ txtCandidateId��5      $
��Left=�6*   $��26��6E   $��	94,59,0,0q�6Q   $
 Wrap=�6d   $��TextBox��6x   $	��Top=�6�   $	��287��6�   �� 5   
   $
��Left=�6   $��26��6-   $��	94,90,0,0q�69   $
 Wrap=�6L   $��TextBox��6`   $	��Top=�6o   $	��287��6�   �� 5   
   $
��Left=�6   $��26��6-   $��
94,121,0,0q�69   $
 Wrap=�6M   $��TextBox��6a   $	��Top=�6p   $	��287��6�   (  "System.Windows.Controls.DatePicker  5   
   $
��Left=�6   $��26��60   $��
94,152,0,0q�6<   $	��Top=�6P   $	��287��6h   �� 5   
   -   5         ��Name$ cboJobPosting��5      $
��Left=�6*   $��
473,59,0,0q�6E   $	��Top=�6Y   $	��317��6q   $��26��6}   6�   &   System.Windows.Controls.DataGrid  5   
   -   5          Name$ datCandidateProfile��5      $��Center=�60   $	��175��6M   $��	0,183,0,0q�6Z   $	��Top=�6m   $	��780��6�   6�   �� 5   
   -   5         ��Name$ btnAdd��5      $	��Add��6!   $
��Left=�6/   $��
10,363,0,0q�6J   $	��Top=�6^   $��61��6�   $	��149��6�   ���	#FF28FF00$
��Bold"�6�   �� 5   
   -   5        $ btnClose��5      $��Close��6#   $
��Left=�63   $��641,363,0,0q�6N   $	��Top=�6c   $��61��6�   $	��149��6�   ���	#FF1DC8D6$
��Bold"�6�   �� 5   
   -   5        $ 	btnUpdate��5      $��Update��6$   $
��Left=�65   $��220,363,0,0q�6P   $	��Top=�6e   $��61��6�   $	��149��6�   $
��Bold"�6�   ���	#FFA5FF236�   �� 5   
   -   5        $ 	btnDelete��5      $��Delete��6$   $
��Left=�65   $��421,363,0,0q�6P   $	��Top=�6e   $��61��6�   $	��149��6�   ���	#FFE8C30F$
��Bold"�6�   5      5      