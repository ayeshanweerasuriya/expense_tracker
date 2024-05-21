const key = "";
const apiUrl = "https://api.openweathermap.org/data/2.5/weather?units=metric";

const searchBtn = document.getElementById("city_input_btn");
const weatherIcon = document.querySelector(".weather-icon");

const checkWeather = async (cityName) => {
  try {
    const response = await fetch(apiUrl + `&q=${cityName}&appid=${key}`);

    if (response.status == 404) {
      document.querySelector(".error").style.display = "block";
      document.getElementById("city_input").value = "";
    } else {
      const data = await response.json();
      document.querySelector(".error").style.display = "none";
      return data;
    }
  } catch (error) {
    console.error("Error fetching weather data:", error);
  }
};

const elementUpdate = async (cityName) => {
  const data = await checkWeather(cityName);

  console.log(data);
  document.querySelector(".city").innerHTML = data.name;
  document.querySelector(".temp").innerHTML = Math.round(data.main.temp) + "°C";
  document.querySelector(".humidity").innerHTML = data.main.humidity + "%";
  document.querySelector(".wind").innerHTML = data.wind.speed + " km/h";

  if (data.weather[0].main == "Clouds") {
    weatherIcon.src = "./assets/images/clouds.png";
  } else if (data.weather[0].main == "Clear") {
    weatherIcon.src = "./assets/images/clear.png";
  } else if (data.weather[0].main == "Rain") {
    weatherIcon.src = "./assets/images/rain.png";
  } else if (data.weather[0].main == "Drizzle") {
    weatherIcon.src = "./assets/images/drizzle.png";
  } else if (data.weather[0].main == "Mist") {
    weatherIcon.src = "./assets/images/mist.png";
  } else if (data.weather[0].main == "Snow") {
    weatherIcon.src = "./assets/images/snow.png";
  } else if (data.weather[0].main == "Wind") {
    weatherIcon.src = "./assets/images/wind.png";
  }

  document.querySelector(".weather").style.display = "block";
};

searchBtn.addEventListener("click", () => {
  const cityName = document.getElementById("city_input").value;
  elementUpdate(cityName);
});
