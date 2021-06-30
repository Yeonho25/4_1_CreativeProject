package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import com.DAO.RestaurantDao;
import com.persistence.Restaurant;
import com.persistence.Tour;

import controller.Restaurant_CRUD;
import controller.Tour_CRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class RestaurantSearchController {
	
	private ObservableList<Restaurant> data;
	private RestaurantDao resDao;
	private String siDo;
	private String area;
	private ObservableList<String> siDolist = FXCollections.observableArrayList("선택","서울특별시", "경기도", "경상남도", "경상북도", "전라남도",
			"전라북도", "대구", "부산광역시", "제주도", "강원도", "충청남도", "충청북도");
	private ObservableList<String> seoul = FXCollections.observableArrayList("종로구", "중구", "용산구", "성동구", "광진구", "동대문구",
			"중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구",
			"서초구", "강남구", "송파구", "강동구");
	private ObservableList<String> gyeonGiDo = FXCollections.observableArrayList("수원시", "성남시", "용인시", "안양시", "안산시",
			"과천시", "광명시", "광주시", "군포시", "부천시", "시흥시", "김포시", "안성시", "오산시", "의왕시", "이천시", "평택시", "하남시", "화성시", "여주시");
	private ObservableList<String> gyeongsangnamdo = FXCollections.observableArrayList("창원시", "김해시", "진주시", "양산시",
			"거제시", "통영시", "사천시", "밀양시", "함안군", "거창군", "창녕군", "고성군", "하동군", "합천군", "남해군", "함양군", "산청군", "의령군");
	private ObservableList<String> gyeongsangbukdo = FXCollections.observableArrayList("포항시", "경주시", "김천시", "안동시",
			"구미시", "영주시", "영천시", "상주시", "문경시", "경산시", "군위군", "의성군", "청송군", "영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군",
			"예천군", "봉화군", "울진군", "을릉군");
	private ObservableList<String> jeollanamdo = FXCollections.observableArrayList("목포시", "여수시", "순천시", "나주시", "광양시",
			"담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군", "장흥군", "강진군", "해남군", "영암군", "무안군", "함평군", "영광군", "장성군", "완도군",
			"진도군", "신안군");
	private ObservableList<String> jeollabukdo = FXCollections.observableArrayList("전주시", "익산시", "군산시", "정읍시", "김제시",
			"남원시", "완주군", "고창군", "부안군", "임실군", "순창군", "진안군", "무주군", "장수군");
	private ObservableList<String> daegu = FXCollections.observableArrayList("중구", "동구", "서구", "남구", "북구", "수성구", "달서구",
			"달성군");
	private ObservableList<String> busan = FXCollections.observableArrayList("중구", "서구", "동구", "영도구", "부산진구", "동래구",
			"남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구", "기장군");
	private ObservableList<String> jejudo = FXCollections.observableArrayList("애월면", "한림면", "안덕면", "중문면", "서귀면", "남원면",
			"표선면", "성산면", "조천면");
	private ObservableList<String> gangwondo = FXCollections.observableArrayList("원주시", "춘천시", "강릉시", "동해시", "속초시",
			"삼척시", "태백시", "홍천군", "철원군", "횡성군", "평창군", "정선군", "영월군", "인제군", "고성군", "양양군", "화천군", "양구군");
	private ObservableList<String> chungcheongnamdo = FXCollections.observableArrayList("천안시", "공주시", "보령시", "아산시",
			"서산시", "논산시", "계룡시", "당진시", "금산군", "부여군", "서천군", "청양군", "홍성군", "예산군", "태안군");
	private ObservableList<String> chungcheongbukdo = FXCollections.observableArrayList("청주시", "충주시", "제천시", "보은군",
			"옥천군", "영동군", "증평군", "진천군", "괴산군", "음성군", "단양군");

	@FXML
	private Button serchBtn;
	@FXML
	private Button topBtn;
	@FXML
	private Button tourBtn;
	@FXML
	private ComboBox<String> comboBox1;
	@FXML
	private ComboBox<String> comboBox2;
	@FXML
	private TextField restaurantName;
	@FXML
	private TableView<Restaurant> addrTable;
	@FXML
	private TableColumn<Restaurant,String> colName;
	@FXML
	private TableColumn<Restaurant,String> colCategory;
	@FXML
	private TableColumn<Restaurant,String> colAddress;
	@FXML
	private TableColumn<Restaurant,String> colStoreTel;

	@FXML
	private void initialize() {
		resDao = new RestaurantDao();
		comboBox1.setItems(siDolist);
		comboBox1.setValue("선택");
		
		addrTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		          if(event.getClickCount()>1) {
//		                 System.out.println("Mouse Double Click!");
		        	  try
		      		{
		      			FXMLLoader loader = new FXMLLoader();
		      			  loader.setLocation(getClass().getResource("/view/RestaurantInformation.fxml"));
		      			  Parent root = (Parent) loader.load();
		      			  Scene scene = new Scene(root);
		      			  
		      			  Restaurant_CRUD pop = loader.getController();
		      			  pop.initData(((Restaurant) addrTable.getSelectionModel().getSelectedItem()).getId());
		      			  
		      			  Stage stage = new Stage();
		      			  stage.setScene(scene);
		      			  stage.show();
		      			stage.show();
		      			
		      		} catch (Exception e)
		      		{
		      			e.printStackTrace();
		      		}
		          }
		     }
		});
	}
	@FXML
	public void changeTourView() {
		try {
			Parent restaurantSearch = FXMLLoader.load(getClass().getResource("/view/TourSearch.fxml"));

			Scene scene = new Scene(restaurantSearch);

			Stage primaryStage = (Stage) tourBtn.getScene().getWindow(); // 현재 윈도우 가져오기

			primaryStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleTop(ActionEvent event)
	{
		  try
    		{
    			FXMLLoader loader = new FXMLLoader();
    			  loader.setLocation(getClass().getResource("/view/ReviewTop.fxml"));
    			  Parent root = (Parent) loader.load();
    			  Scene scene = new Scene(root);
    	
    			  
    			  Stage stage = new Stage();
    			  stage.setScene(scene);
    			  stage.show();
    			stage.show();
    			
    		} catch (Exception e)
    		{
    			e.printStackTrace();
    		}
        
	}
	
	// Event Listener on Button[#serchBtn].onAction
	@FXML
	public void getList(ActionEvent event) {
		if(comboBox1.getValue().equals("선택")) {
			if(!restaurantName.getText().equals("")) {
				showSearchRestaurant(restaurantName.getText());
			} else
			{
			showAllRestaurant();
			}
		}else {
			siDo = comboBox1.getValue();
			area = comboBox2.getValue();
			if(!restaurantName.getText().equals("")) {
				showSearchRestaurant(siDo,area,restaurantName.getText());
			} else
			{
			showSearchRestaurant(siDo,area);
			}
		}
	}
	@FXML
	public void changeRestaurantView() {
		try {
			Parent tourSearch = FXMLLoader.load(getClass().getResource("/view/RestaurantSearch.fxml"));

			Scene scene = new Scene(tourSearch);

			Stage primaryStage = (Stage) tourBtn.getScene().getWindow(); // 현재 윈도우 가져오기

			primaryStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Event Listener on ComboBox[#comboBox1].onAction
	@FXML
	public void comboChanged(ActionEvent event) {
		if (comboBox1.getValue().equals("서울특별시"))
			comboBox2.setItems(seoul);
		else if (comboBox1.getValue().equals("경기도"))
			comboBox2.setItems(gyeonGiDo);
		else if (comboBox1.getValue().equals("경상남도"))
			comboBox2.setItems(gyeongsangnamdo);
		else if (comboBox1.getValue().equals("경상북도"))
			comboBox2.setItems(gyeongsangbukdo);
		else if (comboBox1.getValue().equals("전라남도"))
			comboBox2.setItems(jeollanamdo);
		else if (comboBox1.getValue().equals("전라북도"))
			comboBox2.setItems(jeollabukdo);
		else if (comboBox1.getValue().equals("대구"))
			comboBox2.setItems(daegu);
		else if (comboBox1.getValue().equals("부산광역시"))
			comboBox2.setItems(busan);
		else if (comboBox1.getValue().equals("제주도"))
			comboBox2.setItems(jejudo);
		else if (comboBox1.getValue().equals("강원도"))
			comboBox2.setItems(gangwondo);
		else if (comboBox1.getValue().equals("충청남도"))
			comboBox2.setItems(chungcheongnamdo);
		else if (comboBox1.getValue().equals("충청북도"))
			comboBox2.setItems(chungcheongbukdo);
		else if(comboBox1.getValue().equals("선택"))
			comboBox2.setItems(null);
	}
	private void showSearchRestaurant(String name) {
		try {
			data = (ObservableList<Restaurant>) resDao.searchRestaurant(name);
			
			colName.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("name"));
			colCategory.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("category"));
			colAddress.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("address"));
			colStoreTel.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("storeTel"));
			
			addrTable.setItems(null);
			addrTable.setItems(data);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Data를 테이블에 가져올 수 없습니다.");
		}
	}
	private void showSearchRestaurant(String siDo, String area) {
		try {
			data = (ObservableList<Restaurant>) resDao.searchRestaurant(siDo,area);
			
			colName.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("name"));
			colCategory.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("category"));
			colAddress.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("address"));
			colStoreTel.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("storeTel"));
			
			addrTable.setItems(null);
			addrTable.setItems(data);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Data를 테이블에 가져올 수 없습니다.");
		}
	}
	private void showSearchRestaurant(String siDo, String area,String name) {
		try {
			data = (ObservableList<Restaurant>) resDao.searchRestaurant(siDo,area,name);
			
			colName.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("name"));
			colCategory.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("category"));
			colAddress.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("address"));
			colStoreTel.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("storeTel"));
			
			addrTable.setItems(null);
			addrTable.setItems(data);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Data를 테이블에 가져올 수 없습니다.");
		}
	}
	private void showAllRestaurant() {
		try {
			data = (ObservableList<Restaurant>) resDao.getRestaurantList();
			
			//TableView 컬럼에 전달객체(DTO) 항목 대응시키기
			colName.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("name"));
			colCategory.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("category"));
			colAddress.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("address"));
			colStoreTel.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("storeTel"));
			
			addrTable.setItems(null);
			addrTable.setItems(data);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Data를 테이블에 가져올 수 없습니다.");
		}
	}
}
