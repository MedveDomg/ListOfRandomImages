package omg.medvedomg.listofrandomimages1;

import javax.inject.Singleton;

import dagger.Component;
import omg.medvedomg.listofrandomimages1.ui.activity.MainActivity;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
}