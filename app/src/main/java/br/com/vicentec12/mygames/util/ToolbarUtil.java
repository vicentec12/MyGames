package br.com.vicentec12.mygames.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ToolbarUtil {

    public static void configurarToolbar(AppCompatActivity activity, Toolbar toolbar) {
        configurarToolbar(activity, toolbar, false, null);
    }

    public static void configurarToolbar(AppCompatActivity activity, Toolbar toolbar, boolean temSetaVoltar) {
        configurarToolbar(activity, toolbar, temSetaVoltar, null);
    }

    public static void configurarToolbar(AppCompatActivity activity, Toolbar toolbar, String titulo) {
        configurarToolbar(activity, toolbar, false, titulo);
    }

    public static void configurarToolbar(AppCompatActivity activity, Toolbar toolbar, boolean temSetaVoltar, String titulo) {
//        UsuarioRepository usuarioRepository = InicializacaoUtil.inicializarUsuarioRepository(activity);
//        usuarioRepository.recuperarUsuario(new UsuarioDataSource.OnRecuperarUsuarioCallback() {
//            @Override
//            public void onUsuarioCarregado(Usuario usuario) {
//                layout_toolbar.setSubtitle(usuario.getMatriculaNome());
//                if (titulo != null) layout_toolbar.setTitle(titulo);
//                activity.setSupportActionBar(layout_toolbar);
//                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(temSetaVoltar);
//                activity.getSupportActionBar().setDisplayShowHomeEnabled(temSetaVoltar);
//            }
//
//            @Override
//            public void onSemDados() {
//                activity.setSupportActionBar(layout_toolbar);
//            }
//        });
    }

    /**
     * Método responsável por habilitar e desabilitar a expansão do {@link CollapsingToolbarLayout}
     * utilizando o Scroll.
     *
     * @param mAppBarLayout     {@link AppBarLayout} que deseja habilitar/desabilitar expansão.
     * @param mNestedScrollView {@link NestedScrollView} responsável pela expansão por scroll.
     * @param mExpandedToolbar  Responsável por habilitar ou desabilitar expansão.
     */
    public static void setCollapsingToolbarExpandable(@NonNull NestedScrollView mNestedScrollView, @NonNull AppBarLayout mAppBarLayout,
                                                      @NonNull boolean mExpandedToolbar) {
        mAppBarLayout.setExpanded(mExpandedToolbar, true);
        mNestedScrollView.setNestedScrollingEnabled(mExpandedToolbar);
        // Habilita/desabilita evento de "puxar" do layout_toolbar referente
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return mExpandedToolbar;
                }
            });
        }
    }

}
